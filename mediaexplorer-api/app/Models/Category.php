<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Category extends Model
{
    protected $keyType = 'string';
    public $incrementing = false;

    protected $fillable = [
        'id',
        'name',
        'description',
        'image_uri'
    ];

    public function contents(): HasMany
    {
        return $this->hasMany(Content::class, 'categoria_id');
    }
}